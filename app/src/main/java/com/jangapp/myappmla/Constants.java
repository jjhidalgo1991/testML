package com.jangapp.myappmla;


import com.jangapp.myappmla.repository.storge.model.ProdOverView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Constants {
    // network
    public static final Type PROD_ARRAY_LIST_CLASS_TYPE = (new ArrayList<ProdOverView>()).getClass();

    public static final String LIMIT_REQUEST_PARAM = "limit";
    public static final String OFFSET_REQUEST_PARAM = "offset";
    public static final String QUERY_REQUEST_PARAM = "q";

    public static final int LOADING_PAGE_SIZE = 20;
    // DB
    public static final String DATA_BASE_NAME = "MLAa.db";
    public static final int NUMBERS_OF_THREADS = 3;
    public static final String RESULT_ARRAY_DATA_TAG = "results";
    public static final String MLA_BASE_URL = "https://api.mercadolibre.com";

}
