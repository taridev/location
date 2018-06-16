package org.matthieuaudemard.location.vue.datepicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {

	private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	public DateLabelFormatter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object stringToValue(String arg0) throws ParseException {
		return dateFormatter.parseObject(arg0);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}
