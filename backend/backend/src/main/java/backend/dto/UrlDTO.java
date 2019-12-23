package backend.dto;

public class UrlDTO {
	/*Pomocna klasa da ne bih slao url u url-u (kao path variable) spring ga tretira kao hakerski napad
	 * pakujem url u Request body */
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	

}
