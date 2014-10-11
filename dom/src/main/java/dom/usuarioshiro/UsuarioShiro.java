/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package dom.usuarioshiro;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Bounded;
import org.apache.isis.applib.annotation.DescribedAs;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.ObjectType;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Title;

import dom.rol.Rol;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy = javax.jdo.annotations.IdGeneratorStrategy.IDENTITY, column = "id")
@javax.jdo.annotations.Version(strategy = VersionStrategy.VERSION_NUMBER, column = "version")
@ObjectType("UsuarioShiro")
@Bounded
public class UsuarioShiro {

	private String nick;

	@MemberOrder(sequence = "1")
	@Column(allowsNull = "false")
	@Title
	public String getNick() {
		return nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	private String password;

	@MemberOrder(sequence = "2")
	@Column(allowsNull = "false")
	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Join
	@Element(dependent = "false")
	private SortedSet<Rol> listaDeRoles = new TreeSet<Rol>();

	@MemberOrder(sequence = "3")
	@Render(org.apache.isis.applib.annotation.Render.Type.EAGERLY)
	public SortedSet<Rol> getListaDeRoles() {
		return listaDeRoles;
	}

	public void setRolesList(final SortedSet<Rol> listaDeRoles) {
		this.listaDeRoles = listaDeRoles;
	}

	@MemberOrder(sequence = "3")
	@Named("Agregar Rol")
	@DescribedAs("Agrega un Rol al Usuario.")
	public UsuarioShiro addRole(final @Named("Role") Rol rol) {

		listaDeRoles.add(rol);

		return this;
	}

	@MemberOrder(sequence = "5")
	@Named("Eliminar")
	public UsuarioShiro removeRole(final @Named("Rol") Rol rol) {

		getListaDeRoles().remove(rol);
		return this;
	}

	public SortedSet<Rol> choices0RemoveRole() {
		return getListaDeRoles();
	}

	@javax.inject.Inject
	DomainObjectContainer container;

}