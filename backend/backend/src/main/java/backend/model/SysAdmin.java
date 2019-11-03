package backend.model;

import javax.persistence.*;


@Entity
@DiscriminatorValue("sys")
public class SysAdmin extends User {
	private static final long serialVersionUID = 7753801000941991684L;

	public SysAdmin() {
		super.setFirstTime(false);	//ne mora da promeni lozinku prvi put
		super.setEnabled(true);		//ne mora da aktivira account
	}
}
