package dom.rol;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Bounded;
import org.apache.isis.applib.annotation.DescribedAs;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Render;

import dom.permiso.Permiso;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@ObjectType("Rol")
@Bounded
public class Rol implements Comparable<Rol> {

	public String title() {
		String text = nombre;
		return text;
	}

	private String nombre;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	@Join
	@Element(dependent = "false")
	private SortedSet<Permiso> listaPermisos = new TreeSet<Permiso>();

	@MemberOrder(sequence = "3")
	@Render(org.apache.isis.applib.annotation.Render.Type.EAGERLY)
	public SortedSet<Permiso> getListaPermisos() {
		return listaPermisos;
	}

	public void setListaPermisos(final SortedSet<Permiso> listaPermisos) {
		this.listaPermisos = listaPermisos;
	}

	@MemberOrder(sequence = "4")
	@Named("Agregar Permiso")
	@DescribedAs("Agregar un Permiso a este Rol.")
	public Rol addPermiso(final @Named("Permission") Permiso permiso) {
		listaPermisos.add(permiso);
		return this;
	}

	@MemberOrder(sequence = "5")
	@Named("Eliminar Permiso")
	@DescribedAs("Remover permiso para este Rol.")
	public Rol removePermiso(final @Named("Permission") Permiso permiso) {
		getListaPermisos().remove(permiso);
		return this;
	}

	public SortedSet<Permiso> choices0RemovePermiso() {
		return getListaPermisos();
	}

	@Override
	public int compareTo(Rol other) {
		return this.getNombre().compareTo(other.getNombre());
	}

}
