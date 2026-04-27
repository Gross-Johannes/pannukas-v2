package net.teymm.pannukas.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.web.bind.WebDataBinder;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalStringTrimmingTest {

    @Test
    void trimsModelAttributeStyleBindingValues() {
        GlobalBindingAdvice advice = new GlobalBindingAdvice();
        TrimTarget target = new TrimTarget();
        WebDataBinder binder = new WebDataBinder(target);

        advice.initBinder(binder);
        binder.bind(new MutablePropertyValues(Map.of(
                "title", "  Pancakes  ",
                "categoryType", "  MAIN_MENU  "
        )));

        assertEquals("Pancakes", target.getTitle());
        assertEquals("MAIN_MENU", target.getCategoryType());
    }

    @Test
    void trimsRequestBodyStrings() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GlobalJacksonStringTrimConfig().stringTrimmingModule());

        TrimTarget target = objectMapper.readValue("""
                {
                  "title": "  Pancakes  ",
                  "categoryType": "  MAIN_MENU  "
                }
                """, TrimTarget.class);

        assertEquals("Pancakes", target.getTitle());
        assertEquals("MAIN_MENU", target.getCategoryType());
    }

    @SuppressWarnings("unused")
    static class TrimTarget {
        private String title;
        private String categoryType;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategoryType() {
            return categoryType;
        }

        public void setCategoryType(String categoryType) {
            this.categoryType = categoryType;
        }
    }
}
