var SignInForm = React.createClass({
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
                  buttonValue="ログイン">

                <GeneralValidationError error={this.state.errors[""]} />

                <InputForm id="email"
                           label="メールアドレス"
                           type="email"
                           name="email"
                           error={this.state.errors["email"]}/>

                <InputForm id="password"
                           label="パスワード"
                           type="password"
                           name="password"
                           error={this.state.errors["password"]}/>
            </Form>
        )
    }
});

var SignIn = React.createClass({
    render: function () {
        return (
            <div className="row">
                <div className="col-sm-4"></div>
                <div className="col-sm-4">
                    <h1>ログイン</h1>
                    <SignInForm postUrl={this.props.postUrl}
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
    <SignIn postUrl={postUrl}
            redirectUrl={redirectUrl}/>,
    document.getElementById('content')
);