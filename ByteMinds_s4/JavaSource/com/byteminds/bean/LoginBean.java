package com.byteminds.bean;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
//import tecnofenix.entidades.Usuario;
import tecnofenix.entidades.Usuario;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.byteminds.negocio.AnalistaDTO;
import com.byteminds.negocio.EstudianteDTO;
import com.byteminds.negocio.GestionUsuarioService;
import com.byteminds.negocio.TutorDTO;
import com.byteminds.negocio.UsuarioDTO;
import com.byteminds.remoto.EJBUsuarioRemoto;
import com.byteminds.utils.AuthService;

import java.io.IOException;
import java.io.Serializable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.util.Hashtable;

@Named(value = "loginBean") // JEE8
@SessionScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String token = "token";
	private UsuarioDTO userioLogeado;
	private EJBUsuarioRemoto ejbRemoto;
	private GestionUsuarioService gUS;
	private AuthService auth;

	@PostConstruct
	public void init() {
		auth = new AuthService();
		gUS = new GestionUsuarioService();
	}

	// Este metodo debe retornar el token y mandar la redireccion al index
	public String login() {
		Usuario user = validateUsernamePassword();

		FacesMessage message = null;

		if (user != null) {
			userioLogeado = gUS.fromUsuario(user);
			if(userioLogeado.getValidado()==false) {
				message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Su usuario todavia no fue validado");
				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
				return null;	
			}
			
			this.token =auth.createJWT(String.valueOf(user.getId()), "ByteMindsApp", user.getApellidos()+user.getNombres(), 3600000);//El token dura 1 hora
			// Save the JWT token to session or somewhere else
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("jwt", token);
						
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido " +" ["+ userioLogeado.getUTipo()+"] " + userioLogeado.getNombres()+" "+userioLogeado.getApellidos() , username);
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
		return "/login?faces-redirect=true";
	}

	private Usuario validateUsernamePassword() {
		if(username==null||password==null||username.equals("")||password.equals("")) {
			return null;
		}
		
		Usuario user = null;
		try {
			ejbRemoto = new EJBUsuarioRemoto();
			ejbRemoto.ping();
			user = ejbRemoto.login(username, password);
			ejbRemoto.ejecutarMetodo();

		} catch (Exception e) {
			System.out.println(e);
		}

		return user;
	}

	
	
	 private Usuario loginActiveDirectory(String username, String password) {
	        Usuario user = null;
	        String ldapAdServer = "ldap://your-ldap-server:389"; // Cambia esto por tu servidor LDAP
	        String ldapSearchBase = "dc=example,dc=com"; // Cambia esto por tu base de búsqueda LDAP
	        String ldapUsername = "cn=admin,dc=example,dc=com"; // Usuario administrador LDAP
	        String ldapPassword = "adminpassword"; // Contraseña del usuario administrador
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
	                    System.err.println("Matched multiple users for the accountName: " + ldapAccountToLookup);
	                    return null;
	                }
	            }

	            if (searchResult == null) {
	                System.err.println("Account not found: " + ldapAccountToLookup);
	                return null;
	            }

	            Attributes attrs = searchResult.getAttributes();
	            String userDN = searchResult.getNameInNamespace();

	            // Authenticate with the user's credentials
	            env.put(Context.SECURITY_PRINCIPAL, userDN);
	            env.put(Context.SECURITY_CREDENTIALS, password);

	            new InitialDirContext(env); // This will throw an exception if the authentication fails

	            // Get the user's attributes FROM LDAP ACTIVE DIRECTORY
//	            user = new Usuario();
//	            user.setNombre(attrs.get("cn").get().toString());
//	            user.setEmail(attrs.get("mail").get().toString());
	            // Otros atributos que quieras obtener del usuario

	            ctx.close();
	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        return user;
	    }
	public boolean esTutor() {
		return this.userioLogeado instanceof TutorDTO;
	}

	public boolean esEstudiante() {
		return this.userioLogeado instanceof EstudianteDTO;
	}

	public boolean esAnalista() {
		return this.userioLogeado instanceof AnalistaDTO;
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

	public UsuarioDTO getUserioLogeado() {
		return userioLogeado;
	}

	public void setUserioLogeado(UsuarioDTO userioLogeado) {
		this.userioLogeado = userioLogeado;
	}

	public GestionUsuarioService getgUS() {
		return gUS;
	}

	public void setgUS(GestionUsuarioService gUS) {
		this.gUS = gUS;
	}
}
