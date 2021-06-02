package nl.hu.bep.demo.ShopList.persistence;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import nl.hu.bep.demo.ShopList.model.World;
//import nl.hu.bep.countrycase.model.World;

import java.io.*;

public class PersistenceManager {
    private static final String ENDPOINT = "https://bep1jensrijks.blob.core.windows.net/";
    private static final String SASTOKEN = "?sv=S283z5tkTV2Sde9lS5E2dCpvnXvc5uF3I9Smtcbb6DU0cpzPNiVpV94frJpgYPKjROI5TEC5Qgr4lb2+1gZgjw==3D";
    private static final String CONTAINER = "Worldcontainer";

    private static BlobContainerClient blobContainer = new BlobContainerClientBuilder()
            .endpoint(ENDPOINT)
            .sasToken(SASTOKEN)
            .containerName(CONTAINER)
            .buildClient();

    private PersistenceManager() {
    }

    public static void loadWorldFromAzure() throws IOException, ClassNotFoundException {
        if (blobContainer.exists()) {
            BlobClient blob = blobContainer.getBlobClient("world_blob");

            if (blob.exists()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                blob.download(baos);

                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bais);

                World loadedWorld = (World)ois.readObject();
                World.setWorld(loadedWorld);

                baos.close();
                ois.close();
            } else throw new IllegalStateException("container not found, loading default data");
        }
    }

    public static void saveWorldToAzure() throws IOException {
        if (!blobContainer.exists()) {
            blobContainer.create();
        }

        BlobClient blob = blobContainer.getBlobClient("world_blob");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(World.getWorld());

        byte[] bytez = baos.toByteArray();

        ByteArrayInputStream bais = new ByteArrayInputStream(bytez);
        blob.upload(bais, bytez.length, true);

        oos.close();
        bais.close();
    }
}