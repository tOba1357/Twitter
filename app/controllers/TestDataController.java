package controllers;

import models.entity.User;
import models.requset.SaveUserRequest;
import models.service.TweetService;
import models.service.UserService;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author Tatsuya Oba
 */
public class TestDataController extends Controller {
    private final UserService userService = new UserService();

        public Result makeUser() {
        final String email = "a@gmail.com";
        final SaveUserRequest userRequest = new SaveUserRequest(
                email,
                "a",
                "テストユーザ"
        );
        if(!userService.saveUser(userRequest)){
            return badRequest("failed");
        }

        final User user = userService.getUserByEmail(email);
        return ok(user.id.toString());
    }
}
