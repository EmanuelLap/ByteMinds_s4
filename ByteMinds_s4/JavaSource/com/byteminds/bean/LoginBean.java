package com.byteminds.bean;

import javax.annotation.PostConstruct;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.ItrDTO;
import com.byteminds.negocio.RolDTO;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.remoto.EJBUsuarioRemoto;
import com.byteminds.utils.AuthService;

import java.io.IOException;
import java.io.Serializable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

@Named(value = "loginBean") // JEE8
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String token = "token";
	private UsuarioDTO usuarioLogeado;
	private EJBUsuarioRemoto ejbRemoto;
	private GestionUsuarioService gUS;
	private AuthService auth;
	private Boolean activeDirectory;
	private Boolean mostrarContrasenia;
	


	@PostConstruct
	public void init() {
		auth = new AuthService();
		gUS = new GestionUsuarioService();
	}

	// Este metodo debe retornar el token y mandar la redireccion al index
	public String login() {
		
		UsuarioDTO user = validateUsernamePassword();

		FacesMessage message = null;

		if (user != null) {
			usuarioLogeado = user;
			if(usuarioLogeado.getValidado()==false) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Su usuario todavia no fue validado");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return null;	
			}
			if(usuarioLogeado.getActivo()==false) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Su usuario esta desactivado");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return null;	
			}
			
//			this.token =auth.createJWT(String.valueOf(user.getId()), "ByteMindsApp", user.getApellidos()+user.getNombres(), 3600000);//El token dura 1 hora
			this.token =auth.createJWT(String.valueOf(user.getId()), "ByteMindsApp", user.getApellidos()+user.getNombres(), 360000000);//El token dura 41 dias para testing
			// Guarda el JWT token a la sesión
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("jwt", token);
						
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido " +" ["+ usuarioLogeado.getUTipo()+"] " + usuarioLogeado.getNombres()+" "+usuarioLogeado.getApellidos() , username);
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

			return "index?faces-redirect=true";
		} else {
			message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Credenciales invalidas");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
			return null;// return "login?faces-redirect=false";
		}
	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();
		return "/login.xhtml?faces-redirect=true";
	}

	public String recuperarContrasenia() {
		return "recuperar.xhtml?faces-redirect=true";
	}
	
	
	private UsuarioDTO validateUsernamePassword() {
		if(username==null||password==null||username.equals("")||password.equals("")) {
			return null;
		}
		if(activeDirectory == true) {
			
			return loginActiveDirectory(username,  password);
			
		}else {
			UsuarioDTO user = null;
			try {
				ejbRemoto = new EJBUsuarioRemoto();
				ejbRemoto.ping();
				user = gUS.login(username, password);
				ejbRemoto.ejecutarMetodo();
	
			} catch (Exception e) {
				System.out.println(e);
			}
			return user;
		}
	}

	public void verificarAccesoAnalista() {
	    if (!esAnalista()) {
	        try {
	        	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        	ec.redirect(ec.getRequestContextPath() + "/no-autorizado.xhtml");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void verificarAccesoEstudiante() {
	    if (!esEstudiante()) {
	        try {
	        	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        	ec.redirect(ec.getRequestContextPath() + "/no-autorizado.xhtml");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void verificarAccesoTutor() {
	    if (!esTutor()) {
	        try {
	        	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        	ec.redirect(ec.getRequestContextPath() + "/no-autorizado.xhtml");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	public void verificarAccesoAnalistaOTutor() {
	    if (esEstudiante()) {
	        try {
	        	ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
	        	ec.redirect(ec.getRequestContextPath() + "/no-autorizado.xhtml");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	
	 private UsuarioDTO loginActiveDirectory(String username, String password) {
		 UsuarioDTO user = null;
		 String ldapAdServer = "ldap://192.168.1.200:389"; //  servidor LDAP LOCAL
//		 String ldapAdServer = "ldap://192.168.136.11:389"; //  servidor LDAP GNS3	
	        String ldapSearchBase = "dc=utec,dc=edu,dc=uy";
	        String ldapUsername = "cn=Administrator,cn=Users,dc=utec,dc=edu,dc=uy"; // Usuario administrador LDAP
	        String ldapPassword = "Pfinal2024.01"; // Contraseña del usuario administrador
	        String ldapAccountToLookup = username;

	        Hashtable<String, Object> env = new Hashtable<String, Object>();
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	        env.put(Context.PROVIDER_URL, ldapAdServer);
	        env.put(Context.SECURITY_AUTHENTICATION, "simple");
	        env.put(Context.SECURITY_PRINCIPAL, ldapUsername);
	        env.put(Context.SECURITY_CREDENTIALS, ldapPassword);

	        try {
	            DirContext ctx = new InitialDirContext(env);
	            SearchControls searchControls = new SearchControls();
	            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	            String searchFilter = "(&(objectClass=user)(sAMAccountName=" + ldapAccountToLookup + "))";

	            NamingEnumeration<SearchResult> results = ctx.search(ldapSearchBase, searchFilter, searchControls);

	            SearchResult searchResult = null;
	            if (results.hasMoreElements()) {
	                searchResult = results.nextElement();
	                if (results.hasMoreElements()) {
	                    System.err.println("Existen muchos usuarios para el nombre de cuenta: " + ldapAccountToLookup);
	                    return null;
	                }
	            }

	            if (searchResult == null) {
	                System.err.println("Account not found: " + ldapAccountToLookup);
	                return null;
	            }

	            Attributes attrs = searchResult.getAttributes();
	            String userDN = searchResult.getNameInNamespace();

	            // Autenticacion con las credenciales en el LDAP
	            env.put(Context.SECURITY_PRINCIPAL, userDN);
	            env.put(Context.SECURITY_CREDENTIALS, password);

	            new InitialDirContext(env); // Esto tira una exception si falla la autenticacion


	            System.out.println("LLEGO AL USUARIO Y SE LOGEO CORRECTAMENTE");
	            // Get the user's attributes FROM LDAP ACTIVE DIRECTORY
//	            List<String> lGrup=this.getUserGroups(ctx, username);
//	            lGrup.stream().forEach(System.out::println);
	            
	            //active directory propertie job title
//	           String jobTitleTIPO = attrs.get("title") != null ? attrs.get("title").get().toString() : "ESTUDIANTE";
//	            user = verificarUsuarioEnBase(username, jobTitleTIPO);
//	            if(user==null) {
//	            	user = new AnalistaDTO();
//		            
//
//		            user.setNombres(attrs.get("givenName") != null ? attrs.get("givenName").get().toString() : "NombreGenerico");
//		            user.setApellidos(attrs.get("sn") != null ? attrs.get("sn").get().toString() : "APellidoGenerico");
//		            user.setMail(attrs.get("mail") != null ? attrs.get("mail").get().toString() : "mailgenerico@nodomain.com");
//		            user.setUTipo("ANALISTA");
//		            user.setActivo(true);
//		            user.setDepartamento(attrs.get("st") != null ? attrs.get("st").get().toString() : "Durazno");
//		            user.setGenero("M");
//		            user.setFechaNacimiento(new Date(System.currentTimeMillis()));
//		            user.setId(255);
//		            user.setLocalidad(attrs.get("l") != null ? attrs.get("l").get().toString() : "SinLocalidad");
//		            user.setUsuario(username);
//		            user.setContrasenia(password);
//		            user.setValidado(true);
//		            user.setMailPersonal("sinmail@nodomain.com");
//		            user.setDocumento(attrs.get("employeeNumber") != null ? Integer.parseInt(attrs.get("employeeNumber").get().toString()) : 10000001);
//		            user.setRol(new RolDTO());
//		            user.setTelefono(attrs.get("telephoneNumber") != null ? attrs.get("telephoneNumber").get().toString() : "099999999");
//		            user.setItr(new ItrDTO(1,"Durazno","Durazno [Centro Sur]",true));  
//	            }
	            
	            user = new AnalistaDTO();
	            user.setNombres(attrs.get("givenName").get().toString());
	            user.setApellidos(attrs.get("sn").get().toString());
	            user.setMail(attrs.get("mail").get().toString());
	            user.setUTipo("ANALISTA");
	            user.setActivo(true);
	            user.setDepartamento("Durazno");
	            user.setGenero("M");
	            user.setFechaNacimiento(new Date(System.currentTimeMillis()));
	            user.setId(270);
	            user.setLocalidad("Invitado");
	            user.setUsuario("userinvitado");
	            user.setContrasenia("123456");
	            user.setValidado(true);
	            user.setMailPersonal("mailPrueba@gmail.com");
	            user.setDocumento(11111111);
	            user.setRol(new RolDTO());
	            user.setTelefono("099999999");
	            user.setItr(new ItrDTO(1,"Durazno","Durazno [Centro Sur]",true));		

	            ctx.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        return user;
	    }
	 
	 
	 
	 public UsuarioDTO verificarUsuarioEnBase(String documento, String tipo) {
		 UsuarioDTO user = null;
		 
		 user= gUS.buscarUsuarioPorDocumento(documento, tipo);
		 
		 return user;
	 }
	 
	 public List<String> getUserGroups(DirContext ctx, String userName) throws NamingException {
		    List<String> groups = new ArrayList<>();
		    String searchFilter = "(&(objectClass=user)(sAMAccountName=" + userName + "))";
		    String[] attrIDs = { "memberOf" };
		    SearchControls ctls = new SearchControls();
		    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		    ctls.setReturningAttributes(attrIDs);
		    
		    NamingEnumeration<SearchResult> answer = ctx.search("dc=utec,dc=edu,dc=uy", searchFilter, ctls);
		    while (answer.hasMore()) {
		        SearchResult sr = answer.next();
		        Attributes attrs = sr.getAttributes();
		        if (attrs != null) {
		            Attribute attr = attrs.get("memberOf");
		            if (attr != null) {
		                for (int i = 0; i < attr.size(); i++) {
		                    groups.add(attr.get(i).toString());
		                }
		            }
		        }
		    }
		    return groups;
		}

	 
	  
	public boolean esTutor() {
		return this.usuarioLogeado instanceof TutorDTO;
	}

	public boolean esEstudiante() {
		return this.usuarioLogeado instanceof EstudianteDTO;
	}

	public boolean esAnalista() {
		return this.usuarioLogeado instanceof AnalistaDTO;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UsuarioDTO getUsuarioLogeado() {
		return usuarioLogeado;
	}

	public void setUsuarioLogeado(UsuarioDTO usuarioLogeado) {
		this.usuarioLogeado = usuarioLogeado;
	}

	public GestionUsuarioService getgUS() {
		return gUS;
	}

	public void setgUS(GestionUsuarioService gUS) {
		this.gUS = gUS;
	}
	
	public Boolean getActiveDirectory() {
		return activeDirectory;
	}

	public void setActiveDirectory(Boolean activeDirectory) {
		this.activeDirectory = activeDirectory;
	}

	public Boolean getMostrarContrasenia() {
		return mostrarContrasenia;
	}

	public void setMostrarContrasenia(Boolean mostrarContrasenia) {
		this.mostrarContrasenia = mostrarContrasenia;
	}

}
