/*
 * This is a software made for inventory control
 * 
 * Copyright (C) 2014, ProyectoTypes
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * 
 * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/
package dom.soporte.estadosoporte;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Optional;

import dom.computadora.hardware.gabinete.disco.Disco.CategoriaDisco;
import dom.computadora.hardware.impresora.Impresora;
import dom.computadora.hardware.monitor.Monitor;
import dom.computadora.software.Software;
import dom.soporte.Soporte;
import dom.tecnico.Tecnico;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "idRecibido")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@javax.jdo.annotations.Uniques({ @javax.jdo.annotations.Unique(name = "recibidoUnique", members = { "idRecibido" }) })
@ObjectType("RECIBIDO")
public class Recepcionado implements IEstado {

	// //////////////////////////////////////
	// Identification in the UI
	// //////////////////////////////////////

	public String title() {
		return "RECIBIDO ";
	}

	public String iconName() {
		return "Recepcionado";
	}

	public Recepcionado(Soporte soporte) {
		this.soporte = soporte;
	}

	// {{ Soporte (property)
	private Soporte soporte;

	@MemberOrder(sequence = "1")
	@javax.jdo.annotations.Column(allowsNull = "true")
	public Soporte getSoporte() {
		return soporte;
	}

	@SuppressWarnings("unused")
	private void setSoporte(final Soporte soporte) {
		this.soporte = soporte;
	}

	// }}

	/**
	 * Permite asignar un Tecnico encargado del Soporte Tecnico.
	 * 
	 * <p>
	 * Verifica que el Tecnico nuevo este disponible, en caso afirmativo lo
	 * vincula con Soporte.
	 * </p>
	 * <p>
	 * Es el encargado de cambiar de estados: Recepcionado -> Reparando.
	 * </p>
	 * 
	 * @param tecnico
	 */
	@Override
	@Hidden
	public void asignarTecnico(final Tecnico tecnico) {

		if (tecnico.estaDisponible()) {
			this.getSoporte().setTecnico(tecnico);
			this.getSoporte().getTecnico().sumaComputadora();
			this.getSoporte().getTecnico()
					.addToComputadora(this.getSoporte().getComputadora());
			this.getSoporte().setEstado(this.getSoporte().getReparando());
		} else {
			this.getSoporte().setTecnico(null);
			this.container
					.informUser("El Tecnico seleccionado no esta disponible.");
		}

	}

	@Override
	@Hidden
	public void solicitarInsumos(final int cantidad, final String producto,
			final String marca, final String modelo) {
		this.container
				.informUser("AVISO: ES NECESARIO ASIGNAR UN TECNICO PARA EL SOPORTE.");

	}

	@Override
	@Hidden
	public void finalizarSoporte() {
		this.container
				.informUser("AVISO: ES NECESARIO ASIGNAR UN TECNICO PARA EL SOPORTE.");
	}

	@Override
	@Hidden
	public void asignarNuevoEquipo(final @Named("Nombre de Equipo") String rotulo,final @Named("IP") String ip, 
			final @Named("MAC") String mac,
			final @Named("HDD Marca ") String marcaDisco,
			final @Named("HDD Categoria ") CategoriaDisco tipoDisco,
			final @Named("HDD Tamaño ") int tamanoDisco,
			final @Named("CPU Modelo ") String modeloProcesador,
			final @Named("RAM Modelo") String modeloRam,
			final @Named("RAM Tamaño") int tamanoRam,
			final @Named("RAM Marca") String marcaRam,
			final @Named("Modelo Motherboard") String modeloMotherboard,
			final @Named("Fabricante") String fabricante,
			final @Optional @Named("Monitor") Monitor monitor,
			final @Optional @Named("Impresora") Impresora impresora,
			final @Optional @Named("Software") Software sotfware) {
		this.container
				.informUser("AVISO: ES NECESARIO ASIGNAR UN TECNICO PARA EL SOPORTE.");
	}

	@Hidden
	public boolean escondeAsignarTecnico() {
		return false;
	}

	@Override
	@Hidden
	public boolean escondeFinalizarSoporte() {
		return true;
	}

	@Override
	@Hidden
	public boolean escondeSolicitarInsumos() {
		return true;
	}

	@Override
	@Hidden
	public boolean escondeLlegaronInsumos() {
		return true;
	}

	@Override
	@Hidden
	public boolean escondeNoHayInsumos() {
		return true;
	}

	@Override
	@Hidden
	public boolean escondeAsignarNuevoEquipo() {
		return true;
	}

	@javax.inject.Inject
	private DomainObjectContainer container;

}
