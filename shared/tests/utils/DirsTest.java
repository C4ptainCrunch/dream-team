package utils;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by bambalaam on 19/04/16.
 */
public class DirsTest {
    String storeSystemOS;
    String storeSystemOSHome;
    String storeSystemUser;

    @Before
    public void setUp() throws Exception {
        storeSystemOS = System.getProperty("os.name");
        storeSystemOSHome = System.getProperty("user.home");
        storeSystemUser = System.getProperty("user.name");
    }

    @After
    public void tearDown() throws Exception {
        System.setProperty("os.name", storeSystemOS);
        System.setProperty("user.home", storeSystemOSHome);
        System.setProperty("user.name", storeSystemUser);
    }

    @Test
    public void testLinux() throws Exception {
        System.setProperty("os.name", "Linux");
        System.setProperty("user.home", "~");

        String fakePathString = "~/.local/share/CreaTiKz";
        Path fakePath = Paths.get(fakePathString);

        assertEquals(fakePath, Dirs.getDataDir());
    }

    @Test
    public void testMacOSX() throws Exception {
        System.setProperty("os.name", "Mac OS X");
        System.setProperty("user.home", "~");

        String fakePathString = "~/Library/Application Support/CreaTiKz";
        Path fakePath = Paths.get(fakePathString);

        assertEquals(fakePath, Dirs.getDataDir());
    }

    @Test
    public void testWindows() throws Exception {
        System.setProperty("user.home", "C:/");
        System.setProperty("user.name", "LocalUser");

        String fakePathString = "C:/Documents and Settings/LocalUser/Application Data/" + "Local Settings/GroupeUn/CreaTiKz";
        Path fakePath = Paths.get(fakePathString);
        System.setProperty("os.name", "Windows 7");

        assertEquals(fakePath, Dirs.getDataDir());
    }
}
