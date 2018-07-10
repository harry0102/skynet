package com.skynet.common.propertie;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Description: TODO
 * @author 470772281@qq.COM:
 * @version 2017-4-9 下午06:23:11
 * 
 */
public class ResourceLoader
{
    private static ResourceLoader loader = new ResourceLoader();
    
    private static Map<String, Properties> loaderMap = new HashMap<String, Properties>();
    
    private ResourceLoader()
    {
    }
    
    public static ResourceLoader getInstance()
    {
        return loader;
    }
    
    public Properties getPropFromProperties(String fileName)
        throws Exception
    {
        
        Properties prop = loaderMap.get(fileName);
        if (prop != null)
        {
            return prop;
        }
        String filePath = null;
        String configPath = System.getProperty("configurePath");
        
        if (configPath == null)
        {
            filePath = this.getClass().getClassLoader().getResource(fileName).getPath();
        }
        else
        {
            filePath = configPath + "/" + fileName;
        }
        prop = new Properties();
        prop.load(new FileInputStream(new File(filePath)));
        
        loaderMap.put(fileName, prop);
        return prop;
    }
}
