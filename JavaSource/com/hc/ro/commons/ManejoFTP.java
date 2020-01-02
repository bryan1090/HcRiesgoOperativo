package com.hc.ro.commons;

import it.sauronsoftware.ftp4j.FTPClient;

import com.hc.ro.negocio.ServicioSisParametro;

import java.io.File;
import java.util.StringTokenizer;

import javax.ejb.EJB;

public class ManejoFTP {
	private FTPClient cliente;
	@EJB
	ServicioSisParametro servicioSisParametro;
	private String ipFTP;
	private String userFTP;
	private String passFTP;

	public ManejoFTP() {
		super();
		cliente = new FTPClient();
	}

	public ManejoFTP(String ipFTP, String userFTP, String passFTP) {
		super();
		cliente = new FTPClient();
		this.ipFTP = ipFTP;
		this.userFTP = userFTP;
		this.passFTP = passFTP;
	}

	public void conectarServidorFtp(String servidor, int puerto,
			String usuario, String password) throws Exception {
		try {
			cliente.connect(servidor, puerto);
			cliente.login(usuario, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se pudo conectar con el servidor: "
					+ e.getMessage());
		}
	}

	public void conectarServidorFtp(String servidor) throws Exception {
		try {

			cliente.connect(servidor);
			cliente.login("coopjep", "Nu3v@Cl4v321");
			// cliente.connect("172.29.1.24");
			// cliente.login("RIES_OPE", "GaisTEndf1952*+");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se pudo conectar con el servidor: "
					+ e.getMessage());
		}
	}

	public void conectarServidorFtp() throws Exception {
		try {

			cliente.connect("localhost");
			cliente.login("coopjep", "12345");
//			cliente.connect("ipFTP");
//			cliente.login(userFTP, passFTP);
			// Credenciales FTP Unifinsa
			// cliente.connect("172.29.1.24");
			// cliente.login("RIES_OPE", "GaisTEndf1952*+");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se pudo conectar con el servidor: "
					+ e.getMessage());
		}
	}

	public void conectarServidorFtp(String servidor, int puerto)
			throws Exception {
		try {
			cliente.connect(servidor, puerto);
			cliente.login("coopjep", "Nu3v@Cl4v321");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se pudo conectar con el servidor: "
					+ e.getMessage());
		}
	}

	public void desconectarServidorFtp() throws Exception {
		try {
			cliente.disconnect(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se pudo terminar la conexión: "
					+ e.getMessage());
		}
	}

	public void renombrarArchivoDirectorio(String pathNameAntiguo,
			String pathNameNuevo) throws Exception {
		try {
			cliente.rename(pathNameAntiguo, pathNameNuevo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se pudo renombrar el archivo: "
					+ e.getMessage());
		}
	}

	public void eliminarArchivo(String path) throws Exception {
		try {
			cliente.deleteFile(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se pudo eliminar el archivo: "
					+ e.getMessage());
		}
	}

	public File descargarArchivo(String pathServidor, String pathLocal)
			throws Exception {
		try {
			StringTokenizer tokens = new StringTokenizer(pathServidor, ".");

			tokens.nextToken();
			File temporal = File
					.createTempFile("req", "." + tokens.nextToken());
			cliente.download(pathServidor, temporal);
			return temporal;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se pudo descargar el archivo: "
					+ e.getMessage());
		}
	}

	public void cargarRequerimiento(File fileLocal) throws Exception {
		try {
			cliente.changeDirectory("/archivos");
			cliente.upload(fileLocal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se puedo subir el archivo: "
					+ e.getMessage());
		}
	}

	public void cargarRequerimiento2(File fileLocal) throws Exception {
		try {
			cliente.changeDirectory("/archivosdetalle");
			cliente.upload(fileLocal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se puedo subir el archivo: "
					+ e.getMessage());
		}
	}

	public void cargarRequerimientoPath(String path) throws Exception {
		try {
			cliente.changeDirectory("/archivos");
			cliente.upload(new java.io.File(path));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("No se puedo subir el archivo: "
					+ e.getMessage());
		}
	}

}
