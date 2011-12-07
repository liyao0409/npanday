package npanday.vendor.impl;

import npanday.vendor.Vendor;
import npanday.vendor.VendorInfo;

import java.io.File;
import java.util.List;

/**
 * A pojo implementing {@link VendorInfo}. Use this, if you need a prototype
 * for searching or you want to build up a copy yourself.
 *
 * @author <a href="mailto:lcorneliussen@apache.org">Lars Corneliussen</a>
 */
public class MutableVendorInfo
    implements VendorInfo
{
    private Vendor vendor;
    private String vendorVersion;
    private String frameworkVersion;

    private File sdkInstallRoot;
    private File installRoot;
    private List<File> executablePaths;

    private boolean isDefault;

    public MutableVendorInfo(){

    }

    public MutableVendorInfo( Vendor vendor, String vendorVersion, String frameworkVersion )
    {
        this.vendor = vendor;
        this.vendorVersion = vendorVersion;
        this.frameworkVersion = frameworkVersion;
    }

    public boolean isDefault()
    {
        return isDefault;
    }

    public void setDefault( boolean aDefault )
    {
        isDefault = aDefault;
    }

    public List<File> getExecutablePaths()
    {
        return executablePaths;
    }

    public void setExecutablePaths( List<File> executablePaths )
    {
        this.executablePaths = executablePaths;
    }

    public Vendor getVendor()
    {
        return vendor;
    }

    public void setVendor( Vendor vendor )
    {
        this.vendor = vendor;
    }

    public String getVendorVersion()
    {
        return vendorVersion;
    }

    public void setVendorVersion( String vendorVersion )
    {
        this.vendorVersion = vendorVersion;
    }

    public String getFrameworkVersion()
    {
        return frameworkVersion;
    }

    public void setFrameworkVersion( String frameworkVersion )
    {
        this.frameworkVersion = frameworkVersion;
    }

    public File getSdkInstallRoot()
    {
        return sdkInstallRoot;
    }

    public void setSdkInstallRoot( File sdkInstallRoot )
    {
        this.sdkInstallRoot = sdkInstallRoot;
    }

    public File getInstallRoot()
    {
        return installRoot;
    }

    public void setInstallRoot( File installRoot )
    {
        this.installRoot = installRoot;
    }

    public String toString()
    {
        return "[Manual Vendor Info for " + vendor + " " + vendorVersion + ", Framework Version = "
            + frameworkVersion + ", Executable Paths = " + ( ( executablePaths != null ) ? executablePaths : "" + "]" );
    }
}
