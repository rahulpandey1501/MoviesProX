package xyz.hollywoodhub.hollywoodhub.helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import xyz.hollywoodhub.hollywoodhub.Annotations;
import xyz.hollywoodhub.hollywoodhub.model.ContentDataModel;
import xyz.hollywoodhub.hollywoodhub.model.EpisodesModel;
import xyz.hollywoodhub.hollywoodhub.model.FilterModel;
import xyz.hollywoodhub.hollywoodhub.model.MovieInfoModel;
import xyz.hollywoodhub.hollywoodhub.rest.gomovies.GoMoviesService;
import xyz.hollywoodhub.hollywoodhub.ui.enums.ContentType;

/**
 * Created by rpandey.ppe on 25/07/17.
 */

public class ParserFactory {

    public static ParserFactory getInstance() {
        return new ParserFactory();
    }

    public List<ContentDataModel> movieContentList(Document document) throws Exception{
        Elements movieElements = JsoupHelper.byClass(JsoupHelper.byClass(document, "movies-list").first(), "ml-item");
        List<ContentDataModel> modelList = new ArrayList<>();
        for (Element movieElement : movieElements) {
            ContentDataModel model = new ContentDataModel();
            model.setContentUrl(JsoupHelper.byTag(movieElement, "a").attr("href"));
            model.setImageUrl(JsoupHelper.byTag(movieElement, "img").attr("data-original"));
            model.setMovieName(JsoupHelper.byTag(movieElement, "a").attr("title"));
            model.setMovieQuality(JsoupHelper.byClass(movieElement, "mli-quality").first().text());
            if (JsoupHelper.byTag(movieElement, "a").hasAttr("rel")) {
                model.setMovieDetailsUrl(JsoupHelper.byTag(movieElement, "a").attr("rel"));
            } else if (JsoupHelper.byTag(movieElement, "a").hasAttr("data-url")) {
                model.setMovieDetailsUrl(JsoupHelper.byTag(movieElement, "a").attr("data-url"));
            }
            model.setContentType(ContentType.MOVIE);
            modelList.add(model);
        }
        return modelList;
    }

    public List<ContentDataModel> tvSeriesContentList(Document document) throws Exception {
        Elements seriesElements = JsoupHelper.byClass(JsoupHelper.byClass(document, "movies-list").first(), "ml-item");
        List<ContentDataModel> modelList = new ArrayList<>();
        for (Element seriesElement : seriesElements) {
            ContentDataModel model = new ContentDataModel();
            model.setContentUrl(JsoupHelper.byTag(seriesElement, "a").attr("href"));
            model.setImageUrl(JsoupHelper.byTag(seriesElement, "img").attr("data-original"));
            model.setMovieName(JsoupHelper.byTag(seriesElement, "a").attr("title"));
            model.setNoOfEpisodes(JsoupHelper.byClass(seriesElement, "mli-eps").first().text());
            if (JsoupHelper.byTag(seriesElement, "a").hasAttr("rel")) {
                model.setMovieDetailsUrl(JsoupHelper.byTag(seriesElement, "a").attr("rel"));
            } else if (JsoupHelper.byTag(seriesElement, "a").hasAttr("data-url")) {
                model.setMovieDetailsUrl(JsoupHelper.byTag(seriesElement, "a").attr("data-url"));
            }
            model.setContentType(ContentType.TV_SERIES);
            modelList.add(model);
        }
        return modelList;
    }

    public List<ContentDataModel> searchContentList(Document document) throws Exception {
        Elements elements = JsoupHelper.byClass(JsoupHelper.byClass(document, "movies-list").first(), "ml-item");
        List<ContentDataModel> contentList = new ArrayList<>();
        for (Element element : elements) {
            ContentDataModel model = new ContentDataModel();
            try {
                model.setContentUrl(JsoupHelper.byTag(element, "a").attr("href"));
                model.setImageUrl(JsoupHelper.byTag(element, "img").attr("data-original"));
                model.setMovieName(JsoupHelper.byTag(element, "a").attr("title"));
                if (JsoupHelper.hasClass(element, "mli-eps")) {
                    model.setContentType(ContentType.TV_SERIES);
                    model.setNoOfEpisodes(JsoupHelper.byClass(element, "mli-eps").first().text());
                } else {
                    model.setContentType(ContentType.MOVIE);
                    model.setMovieQuality(JsoupHelper.byClass(element, "mli-quality").first().text());
                }
                if (JsoupHelper.byTag(element, "a").hasAttr("rel")) {
                    model.setMovieDetailsUrl(JsoupHelper.byTag(element, "a").attr("rel"));
                } else if (JsoupHelper.byTag(element, "a").hasAttr("data-url")) {
                    model.setMovieDetailsUrl(JsoupHelper.byTag(element, "a").attr("data-url"));
                }
            } catch (Exception exception) {
                continue;
            }
            contentList.add(model);
        }
        return contentList;
    }

    public List<ContentDataModel> getSuggestions(Document document) throws Exception {
        return searchContentList(document);
    }

    public List<FilterModel> getFilterList(Document document, String tag, String elementClass, String listClassName) {
        Elements elements = JsoupHelper.byTag(JsoupHelper.byClass(JsoupHelper.byClass(document, elementClass).first(), listClassName).first(), "li");
        List<FilterModel> filterList = new ArrayList<>();
        for (Element element : elements) {
            Element inputTag = JsoupHelper.byTag(element, "input").first();
            String key = inputTag.attr("name");
            String value = inputTag.attr("value");
            String text = JsoupHelper.byTag(element, "label").first().text();
            filterList.add(new FilterModel(tag, key, value, text));
        }
        return filterList;
    }

    // For GoMovies
    @Annotations.GoMovies
    public MovieInfoModel movieDetail(Document document, String movieName, String sourceBaseUrl) throws Exception {
        MovieInfoModel.Builder builder;
        if (sourceBaseUrl.equals(GoMoviesService.BASE_URL)) {
            builder = new MovieInfoModel.Builder(movieName);
            String story = document.getElementsByClass("mvic-desc").first().getElementsByClass("desc").text();
            if (story.contains("123Movies")) {
                story = "Not Available";
            }
            builder.setStory(story);
            Element IMDB_info = document.getElementsByClass("mvic-info").first();
            for (Element e : IMDB_info.getElementsByClass("mvici-left").select("p")) {
                if (e.text().contains("Genre"))
                    builder.setGenre(e.text());
                else if (e.text().contains("Actor"))
                    builder.setCast(e.text());
                else if (e.text().contains("Director"))
                    builder.setDirector(e.text());
                else if (e.text().contains("Country"))
                    builder.setCountry(e.text());
            }
            for (Element e : IMDB_info.getElementsByClass("mvici-right").select("p")) {
                if (e.text().contains("Duration"))
                    builder.setDuration(e.text());
                else if (e.text().contains("Release"))
                    builder.setRelease(e.text());
                else if (e.text().contains("IMDb"))
                    builder.setRating(e.text());
                else if (e.text().contains("Quality"))
                    builder.setQuality(e.text());
            }
            return builder.build();
        }

        return null;
    }

    @Annotations.GoMovies
    public List<EpisodesModel> getMovieEpisode(Document document) {
        List<EpisodesModel> serverList = new ArrayList<>();
        Elements elements = JsoupHelper.byId(document, "list-eps").getElementsByClass("les-content");
        for (Element element : elements) {
            Element anchor = JsoupHelper.byTag(element, "a").first();
            String title = anchor.attr("title");
            String id = anchor.attr("id");
            String dataId = anchor.attr("data-id");
            serverList.add(new EpisodesModel(title, id, dataId));
        }

        return serverList;
    }

    @Annotations.GoMovies
    public List<List<EpisodesModel>> getTVSeriesEpisode(Document document) {
        List<List<EpisodesModel>> serverList = new ArrayList<>();
        Elements elements = JsoupHelper.byId(document, "list-eps").getElementsByClass("les-content");
        for (Element element : elements) {
            List<EpisodesModel> episodesModels = new ArrayList<>();
            Elements anchors = JsoupHelper.byTag(element, "a");
            for (Element anchor : anchors) {
                String title = anchor.attr("title");
                String id = anchor.attr("id");
                String dataId = anchor.attr("data-id");
                episodesModels.add(new EpisodesModel(title, id, dataId));
            }
            serverList.add(episodesModels);
        }

        return serverList;
    }
}
