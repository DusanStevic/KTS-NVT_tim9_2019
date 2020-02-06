package backend.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class HallDTO {
	@NotNull(message = "Hall name is mandatory")
	@Length(min = 1, message = "Hall name is mandatory")
	private String name;

	private int standingNr;
	private int sittingNr;

	/*
	 * ne prosledjuje se prilikom kreiranja lokacije
	 */
	// private Long location_id;

	/*
	 * @NotNull(message = "Sectors are mandatory")
	 * 
	 * @NotEmpty(message = "Sectors are mandatory") private ArrayList<SectorDTO>
	 * sectors;
	 */

	public HallDTO() {
		super();
	}

	public HallDTO(String name, int sittingNr, int standingNr) {
		super();
		this.name = name;
		this.sittingNr = sittingNr;
		this.standingNr = standingNr;
	}

	public HallDTO(String name) {
		super();
		this.name = name;
		// this.sectors = sectors;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStandingNr() {
		return standingNr;
	}

	public void setStandingNr(int standingNr) {
		this.standingNr = standingNr;
	}

	public int getSittingNr() {
		return sittingNr;
	}

	public void setSittingNr(int sittingNr) {
		this.sittingNr = sittingNr;
	}

}
