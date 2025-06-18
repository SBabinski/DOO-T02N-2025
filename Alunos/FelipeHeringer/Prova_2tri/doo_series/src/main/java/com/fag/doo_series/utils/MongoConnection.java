package com.fag.doo_series.utils;

import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fag.doo_series.model.*;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class MongoConnection {
    MongoClient mongoClient;
    MongoDatabase seriesDb;
    MongoCollection<Document> series_collection;

    public MongoConnection() {
        this.mongoClient = new MongoClient("localhost", 27017);
        this.seriesDb = mongoClient.getDatabase("Series_db");
        this.series_collection = seriesDb.getCollection("series_api");
    }

    public void putUserOnMongo(Map<String, Object> userJson) {
        Document user_docs = new Document(userJson);
        series_collection.insertOne(user_docs);
    }

    public Usuario getUserFromMongoByCpf(String cpf) {
        Bson filter = Filters.eq("user_cpf", cpf);
        Document doc = series_collection.find(filter).first();

        if (doc != null) {
            return new Usuario(doc);
        }

        return null;
    }

    public void updateUserNameByCpf(String cpf, String novoNome) {
        Bson filter = Filters.eq("user_cpf", cpf);
        Bson update = Updates.set("user_name", novoNome);
        series_collection.updateOne(filter, update);
    }

    public void updateFavoriteList(String cpf, Series series) {
        Bson filter = Filters.eq("user_cpf", cpf);
        Bson update = Updates.addToSet("favorite_series", series.toJson());
        series_collection.updateOne(filter, update);
    }

    public void updateWatchLaterList(String cpf, Series series) {
        Bson filter = Filters.eq("user_cpf", cpf);
        Bson update = Updates.addToSet("series_watch_later", series.toJson());
        series_collection.updateOne(filter, update);
    }

    public void updateWatchedList(String cpf, Series series) {
        Bson filter = Filters.eq("user_cpf", cpf);
        Bson update = Updates.addToSet("series_watched", series.toJson());
        series_collection.updateOne(filter, update);
    }

    public void removeSeriesFromFavoriteList(String cpf, Series series) {
        Bson fiter = Filters.eq("user_cpf", cpf);
        Bson update = Updates.pull("favorite_series", series.toJson());
        series_collection.updateOne(fiter, update);
    }

    public void removeSeriesFromWatchLaterList(String cpf, Series series) {
        Bson fiter = Filters.eq("user_cpf", cpf);
        Bson update = Updates.pull("series_watch_later", series.toJson());
        series_collection.updateOne(fiter, update);
    }

    public void removeSeriesFromWatchedList(String cpf, Series series) {
        Bson fiter = Filters.eq("user_cpf", cpf);
        Bson update = Updates.pull("series_watched", series.toJson());
        series_collection.updateOne(fiter, update);
    }
}

