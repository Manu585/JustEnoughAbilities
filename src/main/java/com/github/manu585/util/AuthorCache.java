package com.github.manu585.util;

import com.github.manu585.JustEnoughAbilities;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public record AuthorCache(JustEnoughAbilities plugin) {
    private static final Map<String, UUID> authorUuidCache = new ConcurrentHashMap<>();

    public void cacheAuthorsUuidsAsync(List<String> authors) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            for (String author : authors) {
                try {
                    UUID uuid = fetchMojangUuid(author);
                    if (uuid != null) authorUuidCache.put(author, uuid);
                } catch (URISyntaxException | IOException e) {
                    plugin.getLogger().log(Level.WARNING, "Mojang Session servers down. Couldn't fetch UUID for Developer players. [THIS IS A NOTIFICATION, NO BUG]");
                }
            }
        });
    }

    private UUID fetchMojangUuid(String name) throws URISyntaxException, IOException {
        URI uri = new URI("https://api.mojang.com/users/profiles/minecraft/" + name);
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setConnectTimeout(2500);
        connection.setReadTimeout(2500);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        if (connection.getResponseCode() != 200) return null;

        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                jsonBuilder.append(line);
            }

            String json = jsonBuilder.toString();
            if (!json.contains("id")) return null;

            JsonObject object = new Gson().fromJson(json, JsonObject.class);

            // Get UUID from JSON object and convert it into a valid UUID
            return UUID.fromString(object.get("id").getAsString().replaceFirst(
                    "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})",
                    "$1-$2-$3-$4-$5"));
        }
    }

    public boolean isDeveloper(UUID uuid) {
        return authorUuidCache.containsValue(uuid);
    }

    public static Map<String, UUID> getAuthorUuidCache() {
        return Collections.unmodifiableMap(authorUuidCache);
    }
}
