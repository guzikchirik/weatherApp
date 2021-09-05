package com.firstapp.weatherapp.activities.user;

import static java.util.Arrays.asList;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firstapp.weatherapp.MainActivity;
import com.firstapp.weatherapp.R;
import com.firstapp.weatherapp.httpclient.HttpClientDeprecated;
import com.firstapp.weatherapp.model.Address;
import com.firstapp.weatherapp.model.User;
import com.firstapp.weatherapp.tasks.GetUsers;
import com.firstapp.weatherapp.tasks.UserService;
import com.firstapp.weatherapp.utils.PropertyReader;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class AddUserActivity extends Activity {
    private static final String MY_SERVICE_URL = "http://192.168.100.52:4000/api/v1/users";
    private Button createUserBtn;
    private User currentUser;
    private EditText login;
    private List<EditText> allFields = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user_activity);

        createUserBtn = findViewById(R.id.create_user_btn);
        createUserBtn.setOnClickListener(createUserBtnListener());
//        new GetUsers(this).execute();
    }

    private View.OnClickListener createUserBtnListener() {
        return View -> {
            login = findViewById(R.id.login);
            EditText lastName = findViewById(R.id.last_name);
            EditText firstName = findViewById(R.id.first_name);
            EditText middleName = findViewById(R.id.middle_name);
            EditText age = findViewById(R.id.age);
            EditText city = findViewById(R.id.city);
            EditText street = findViewById(R.id.street);
            EditText building = findViewById(R.id.building);
            allFields = asList(login, lastName, firstName, middleName, age, city, street, building);

//            boolean areAllFieldsFilledCorrect = checkFields(login, lastName, firstName, middleName, age, city, street, building);
//            if (areAllFieldsFilledCorrect) {
            if (checkFields(allFields)) {
                createUserBtn.setTextColor(getResources().getColor(R.color.white));
                Address address = new Address()
                        .setCity(city.getText().toString())
                        .setStreet(street.getText().toString())
                        .setBuilding(building.getText().toString());
                currentUser = new User()
                        .setLogin(login.getText().toString())
                        .setLastName(lastName.getText().toString())
                        .setFirstName(firstName.getText().toString())
                        .setMiddleName(middleName.getText().toString())
                        .setAge(Integer.valueOf(age.getText().toString()))
                        .setAddress(address);
                ObjectMapper mapper = new ObjectMapper();
                String currentUserString = null;
                try {
                    currentUserString = mapper.writeValueAsString(currentUser);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                PropertyReader propertyReader = new PropertyReader(this);
                Properties properties = propertyReader.getMyProperties("app.properties");

                UserService userService = new UserService(this);
                userService.addUser(currentUser, currentUserString);

//                String response = HttpClientDeprecated.post(MY_SERVICE_URL, currentUserString);
//                if (isHttpResponseWithError(response)) {
                    clearAllFields(allFields);
                Intent userAdded = new Intent(this, UserAddedActivity.class );
                userAdded.putExtra("login", currentUser.getLogin());
                startActivity(userAdded);
//                } else {
//                    Toast.makeText(AddUserActivity.this, R.string.add_user_impossible, Toast.LENGTH_LONG).show();
//                }
            } else {
                createUserBtn.setTextColor(getResources().getColor(R.color.red));
            }
        };
    }

    private boolean isHttpResponseWithError(String httpResponse) {
        return httpResponse != null && !httpResponse.isEmpty() && httpResponse.contains("Error:");
    }

    private void clearAllFields(List<EditText> fields) {
        clearAllFields(fields.toArray(new EditText[0]));
    }

    private void clearAllFields(EditText... fields) {
        for (EditText field : fields) {
            if (!isFieldEmpty(field)) {
                field.setText("");
            }
        }
    }

    private boolean checkFields(List<EditText> fields) {
        return checkFields(fields.toArray(new EditText[0]));
    }

    private boolean checkFields(EditText... fields) {
        boolean allFieldsAreFilled = true;
        for (EditText field : fields) {
            if (isFieldEmpty(field) && allFieldsAreFilled) {
                allFieldsAreFilled = false;
            }
        }
        return allFieldsAreFilled;
    }

    private boolean isFieldEmpty(EditText field) {
        if (field.getText() == null || field.getText().toString().isEmpty() || field.length() == 0) {
            field.setError(getResources().getString(R.string.empty_field_error));
            return true;
        }
        return false;
    }
}