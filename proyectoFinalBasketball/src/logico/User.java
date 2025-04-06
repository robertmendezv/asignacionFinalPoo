package logico;
import java.io.Serializable;



public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String tipo;
	private String userName;
	private String passw;
	
	public User(String tipo, String userName, String passw) {
		super();
		this.tipo = tipo;
		this.userName = userName;
		this.passw = passw;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	
	
}
