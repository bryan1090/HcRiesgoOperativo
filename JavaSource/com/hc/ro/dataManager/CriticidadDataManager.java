package com.hc.ro.dataManager;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.hc.ro.modelo.RoCriticidad;

@ManagedBean(name="criticidadDataManager")
@SessionScoped
public class CriticidadDataManager {
	private List<RoCriticidad> criticidades;


	public CriticidadDataManager() {
		criticidades =new ArrayList<RoCriticidad>();
	}
	

	public List<RoCriticidad> getCriticidades() {
		return criticidades;
	}

	public void setCriticidades(List<RoCriticidad> criticidades) {
		this.criticidades = criticidades;
	}
	
	
}
