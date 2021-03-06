var SignUpForm = React.createClass({
    getInitialState: function () {
        return {
            errors: {}
        };
    },
    setErrors: function(errors) {
      this.setState({
          errors: errors
      });
    },
    render: function () {
        return (
            <Form formId="signup"
                  postUrl={this.props.postUrl}
                  setErrors={this.setErrors}
                  redirectUrl={this.props.redirectUrl}
                  buttonValue="送信">

                <InputForm id="email"
                           label="メールアドレス（必須）"
                           type="email"
                           defalutValue=""
                           name="email"
                           placeholder="***@gmail.com"
                           error={this.state.errors["email"]}/>

                <InputForm id="password"
                           label="パスワード（必須）"
                           type="password"
                           defalutValue=""
                           name="password"
                           placeholder="********"
                           error={this.state.errors["password"]}/>


                <InputForm id="userName"
                           label="ユーザ名（必須）"
                           type="text"
                           defalutValue=""
                           name="userName"
                           placeholder="テストユーザ"
                           error={this.state.errors["password"]}/>
            </Form>
        )
    }
});

var SignUp = React.createClass({
    render: function () {
        return (
            <div className="row">
                <div className="col-sm-4"></div>
                <div className="col-sm-4">
                    <h1>新規登録</h1>
                    <SignUpForm postUrl={this.props.postUrl}
                                redirectUrl={this.props.redirectUrl}/>
                </div>
                <div className="col-sm-4"></div>
            </div>
        )
    }
});

var signupContainer = document.getElementById("content");
var postUrl = signupContainer.getAttribute("postUrl");
var redirectUrl = signupContainer.getAttribute("redirectUrl");

ReactDOM.render(
    <SignUp postUrl={postUrl}
            redirectUrl={redirectUrl}/>,
    document.getElementById('content')
);