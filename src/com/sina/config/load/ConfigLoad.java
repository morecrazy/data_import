package com.sina.config.load;


public abstract class ConfigLoad {

	//protected Logger logger = Logger.getLogger(ConfigLoad.class);

	
	protected String propertiesfilename;

	public String getPropertiesfilename() {
        return propertiesfilename;
    }

    public void setPropertiesfilename(String propertiesfilename) {
        this.propertiesfilename = propertiesfilename;
    }


	public abstract void load();

}
