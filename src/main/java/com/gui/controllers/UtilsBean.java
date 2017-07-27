package com.gui.controllers;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

@ManagedBean(name="utilsController")
@ViewScoped
public class UtilsBean {


	public static void redirectTo(String url) {
//		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, url);

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		try {
			externalContext.redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

}
