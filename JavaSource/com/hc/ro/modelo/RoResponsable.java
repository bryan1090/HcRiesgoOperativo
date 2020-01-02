package com.hc.ro.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.util.List;

/**
 * The persistent class for the ro_responsable database table.
 * 
 */
@Entity
@Table(name = "ro_responsable")
@NamedQuery(name = "RoResponsable.findAll", query = "SELECT r FROM RoResponsable r")
public class RoResponsable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CODIGO_RESP")
	private int codigoResp;

	@Pattern(message = "El campo Apellido solo debe contener letras", regexp = "^[A-Za-zÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "APELLIDO_RESP")
	private String apellidoResp;

	@Pattern(message = "El campo email debe contener un email válido", regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	@Column(name = "CORREO_RESP")
	private String correoResp;

	@Column(name = "DIRECCION1_RESP")
	private String direccion1Resp;

	@Column(name = "DIRECCION2_RESP")
	private String direccion2Resp;

	@Column(name = "PERMISO_SU_PROS")
	private Double permisoSuPros;
	
	@Column(name = "PERMISO_SU_DEPT")
	private Double permisoSuDept;
	
	@Column(name = "PERMISO_SU_AGIA")
	private Double permisoSuAgia;
	
	
	
	
	@Pattern(message = "El campo Nombre solo debe contener letras", regexp = "^[A-Za-zÑñáéíóúÁÉÍÓÚ ]*$")
	@Column(name = "NOMBRE_RESP")
	private String nombreResp;

	@Pattern(message = "El campo Teléfono solo debe contener números", regexp = "^[0-9]*$")
	@Column(name = "TELEFONO1_RESP")
	private String telefono1Resp;

	@Pattern(message = "El campo Teléfono solo debe contener números", regexp = "^[0-9]*$")
	@Column(name = "TELEFONO2_RESP")
	private String telefono2Resp;

	// bi-directional many-to-one association to RoControle
	@OneToMany(mappedBy = "roResponsable")
	private List<RoControles> roControles;

	@ManyToOne
	@JoinColumn(name = "CODIGO_TRES")
	private RoTipoResp roTipoResp;

	// bi-directional many-to-one association to RoRespAgencia
	@OneToMany(mappedBy = "roResponsable")
	private List<RoRespAgencia> roRespAgencias;

	// bi-directional many-to-one association to SisEmpresa
	@ManyToOne
	@JoinColumn(name = "CODIGO_EMPR")
	private SisEmpresa sisEmpresa;

	// bi-directional many-to-one association to SisSucursal
	@ManyToOne
	@JoinColumn(name = "CODIGO_SUCU")
	private SisSucursal sisSucursal;

	// bi-directional many-to-one association to GenEstado
	@ManyToOne
	@JoinColumn(name = "CODIGO_ESTA")
	private GenEstado genEstado;

	// bi-directional many-to-one association to SisUsuario
	@ManyToOne
	@JoinColumn(name = "CODIGO_USUA")
	private SisUsuario sisUsuario;

	public RoResponsable() {
	}

	public int getCodigoResp() {
		return this.codigoResp;
	}

	public void setCodigoResp(int codigoResp) {
		this.codigoResp = codigoResp;
	}

	public String getApellidoResp() {
		return this.apellidoResp;
	}

	public void setApellidoResp(String apellidoResp) {
		this.apellidoResp = apellidoResp;
	}

	public String getCorreoResp() {
		return this.correoResp;
	}

	public void setCorreoResp(String correoResp) {
		this.correoResp = correoResp;
	}

	public String getDireccion1Resp() {
		return this.direccion1Resp;
	}

	public void setDireccion1Resp(String direccion1Resp) {
		this.direccion1Resp = direccion1Resp;
	}

	public String getDireccion2Resp() {
		return this.direccion2Resp;
	}

	public void setDireccion2Resp(String direccion2Resp) {
		this.direccion2Resp = direccion2Resp;
	}

	public String getNombreResp() {
		return this.nombreResp;
	}

	public void setNombreResp(String nombreResp) {
		this.nombreResp = nombreResp;
	}

	public String getTelefono1Resp() {
		return this.telefono1Resp;
	}

	public void setTelefono1Resp(String telefono1Resp) {
		this.telefono1Resp = telefono1Resp;
	}

	public String getTelefono2Resp() {
		return this.telefono2Resp;
	}

	public void setTelefono2Resp(String telefono2Resp) {
		this.telefono2Resp = telefono2Resp;
	}

	public List<RoRespAgencia> getRoRespAgencias() {
		return this.roRespAgencias;
	}

	public void setRoRespAgencias(List<RoRespAgencia> roRespAgencias) {
		this.roRespAgencias = roRespAgencias;
	}

	public RoRespAgencia addRoRespAgencia(RoRespAgencia roRespAgencia) {
		getRoRespAgencias().add(roRespAgencia);
		roRespAgencia.setRoResponsable(this);

		return roRespAgencia;
	}

	public RoRespAgencia removeRoRespAgencia(RoRespAgencia roRespAgencia) {
		getRoRespAgencias().remove(roRespAgencia);
		roRespAgencia.setRoResponsable(null);

		return roRespAgencia;
	}

	public SisEmpresa getSisEmpresa() {
		return this.sisEmpresa;
	}

	public void setSisEmpresa(SisEmpresa sisEmpresa) {
		this.sisEmpresa = sisEmpresa;
	}

	public SisSucursal getSisSucursal() {
		return this.sisSucursal;
	}

	public void setSisSucursal(SisSucursal sisSucursal) {
		this.sisSucursal = sisSucursal;
	}

	public GenEstado getGenEstado() {
		return this.genEstado;
	}

	public void setGenEstado(GenEstado genEstado) {
		this.genEstado = genEstado;
	}

	public SisUsuario getSisUsuario() {
		return this.sisUsuario;
	}

	public void setSisUsuario(SisUsuario sisUsuario) {
		this.sisUsuario = sisUsuario;
	}

	public RoTipoResp getRoTipoResp() {
		return roTipoResp;
	}

	public void setRoTipoResp(RoTipoResp roTipoResp) {
		this.roTipoResp = roTipoResp;
	}

	public List<RoControles> getRoControles() {
		return roControles;
	}

	public void setRoControles(List<RoControles> roControles) {
		this.roControles = roControles;
	}

	public Double getPermisoSuPros() {
		return permisoSuPros;
	}

	public void setPermisoSuPros(Double permisoSuPros) {
		this.permisoSuPros = permisoSuPros;
	}

	public Double getPermisoSuDept() {
		return permisoSuDept;
	}

	public void setPermisoSuDept(Double permisoSuDept) {
		this.permisoSuDept = permisoSuDept;
	}

	public Double getPermisoSuAgia() {
		return permisoSuAgia;
	}

	public void setPermisoSuAgia(Double permisoSuAgia) {
		this.permisoSuAgia = permisoSuAgia;
	}

}