package dom.movimiento.estadoComputadora;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Audited;
import org.apache.isis.applib.annotation.Bookmarkable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.ObjectType;

import dom.movimiento.Movimiento;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "idRecibido")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Uniques({ @javax.jdo.annotations.Unique(name = "recibidoUnique", members = { "idRecibido" }) })
@ObjectType("RECIBIDO")
@Audited
@Bookmarkable
public class Recepcionado implements IEstado {

	public String title() {
		return "RECIBIDO ";
	}

	public String iconName() {
		return "sector";
	}
	// {{ Movimiento (property)
	private Movimiento movimiento;

	@MemberOrder(sequence = "1")
	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(final Movimiento movimiento) {
		this.movimiento = movimiento;
	}
	// }}
	public Recepcionado(Movimiento unMovimiento)
	{
		this.movimiento= unMovimiento;
	}

	@Override
	public void asignarTecnico() {
		this.getMovimiento().setEstadoActual("CAMBIA DE ESTADO A REPARANDO - EN REPARACION.");
		this.getMovimiento().getTecnico().sumaComputadora();
		this.getMovimiento().setEstado(this.getMovimiento().getReparando());
		// this.container
		// .informUser("El Tecnico seleccionado no esta disponible.");

	}

	@Override
	public void esperarRepuestos( ) {
		this.getMovimiento().setEstadoActual("NO ESPERA: NO ES EL ESTADO REPARANDO.");
	}

	@Override
	public void finalizarSoporte() {
		this.getMovimiento().setEstadoActual("NO FINALIZA: NO ES EL ESTADO REPARANDO.");
	}

	@Override
	public void noHayRepuestos( ) {
		// TODO Auto-generated method stub
		this.getMovimiento().setEstadoActual("NO CANCELA: NO ES EL ESTADO ESPERANDO");
	}

	@Override
	public void llegaronRepuestos( ) {
		this.getMovimiento().setEstadoActual("NO ES EL ESTADO ESPERANDO");
	}

	@javax.inject.Inject
	private DomainObjectContainer container;

}
