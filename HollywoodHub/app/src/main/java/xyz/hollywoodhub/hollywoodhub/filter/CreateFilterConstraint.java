package xyz.hollywoodhub.hollywoodhub.filter;

import org.jsoup.nodes.Document;

import xyz.hollywoodhub.hollywoodhub.filter.filters.CountryFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.FilmTypeFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.GenreFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.QualityFilter;
import xyz.hollywoodhub.hollywoodhub.filter.filters.ReleaseFilter;
import xyz.hollywoodhub.hollywoodhub.helper.ParserFactory;

import static xyz.hollywoodhub.hollywoodhub.helper.JsoupHelper.byClass;
import static xyz.hollywoodhub.hollywoodhub.helper.JsoupHelper.byTag;

/**
 * Created by rpandey.ppe on 23/07/17.
 */

public class CreateFilterConstraint {

    private final String GENRE_CLASS = "fc-genre";
    private final String FILM_TYPE_CLASS = "fc-filmtype";
    private final String QUALITY_CLASS = "fc-quality";
    private final String COUNTRY_CLASS = "fc-country";
    private final String RELEASE_CLASS = "fc-release";

    private final String GENRE_LIST_CLASS = "fc-genre-list";
    private final String FILM_TYPE_LIST_CLASS = "fc-filmtype-list";
    private final String QUALITY_LIST_CLASS = "fc-quality-list";
    private final String COUNTRY_LIST_CLASS = "fc-country-list";
    private final String RELEASE_LIST_CLASS = "fc-release-list";

    private Document document;

    private FilmTypeFilter filmTypeFilter;
    private GenreFilter genreFilter;
    private QualityFilter qualityFilter;
    private ReleaseFilter releaseFilter;
    private CountryFilter countryFilter;

    public CreateFilterConstraint(Document document) {
        this.document = document;
        filmTypeFilter = new FilmTypeFilter();
        genreFilter = new GenreFilter();
        qualityFilter = new QualityFilter();
        releaseFilter = new ReleaseFilter();
        countryFilter = new CountryFilter();
        createFilters();
    }

    private void createFilters() {
        ParserFactory parserHelper = ParserFactory.getInstance();
        filmTypeFilter.setFilters(parserHelper.getFilterList(document, FilmTypeFilter.TAG, FILM_TYPE_CLASS, FILM_TYPE_LIST_CLASS));
        genreFilter.setFilters(parserHelper.getFilterList(document, GenreFilter.TAG, GENRE_CLASS, GENRE_LIST_CLASS));
        qualityFilter.setFilters(parserHelper.getFilterList(document, QualityFilter.TAG, QUALITY_CLASS, QUALITY_LIST_CLASS));
        releaseFilter.setFilters(parserHelper.getFilterList(document, ReleaseFilter.TAG, RELEASE_CLASS, RELEASE_LIST_CLASS));
        countryFilter.setFilters(parserHelper.getFilterList(document, CountryFilter.TAG, COUNTRY_CLASS, COUNTRY_LIST_CLASS));
    }

    public FilmTypeFilter getFilmTypeFilter() {
        return filmTypeFilter;
    }

    public GenreFilter getGenreFilter() {
        return genreFilter;
    }

    public QualityFilter getQualityFilter() {
        return qualityFilter;
    }

    public ReleaseFilter getReleaseFilter() {
        return releaseFilter;
    }

    public CountryFilter getCountryFilter() {
        return countryFilter;
    }
}