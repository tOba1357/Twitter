var TweetNode = React.createClass({
    render: function () {
        return (
            <div className="tweet">
                <dl>
                    <dt>{this.props.author}</dt>
                    <dd>{this.props.children}</dd>
                </dl>
            </div>
        );
    }
});

var TweetList = React.createClass({
    getInitialState: function () {
        return {
            tweets: []
        };
    },
    componentDidMount: function() {
        this.setTestData();
    },
    setTestData: function () {
        var testData = [
            {author: "testUser", content: "あけおめー"},
            {author: "あげあし" , content: "あげあしさああああああああああああああああああああああああああああん"}
        ];
        this.setState({
           tweets: testData
        });
    },
    render: function () {
        var tweetNodes = this.state.tweets.map(function (tweet, key) {
            return (
                <TweetNode author={tweet.author} key={key}>
                    {tweet.content}
                </TweetNode>
            );
        });
        return (
            <div className="tweetList">
                {tweetNodes}
            </div>
        );
    }
});

var TweetForm = React.createClass({
    render: function () {
        return (
            <form>
                <p><textarea className="form-control" rows="4" placeholder="ツイート内容"></textarea></p>
                <input className="btn btn-primary pull-right" type="submit"/>
            </form>
        )
    }
});

var TweetBox = React.createClass({
    render: function () {
        return (
            <div className="row">
                <div className="col-sm-4"></div>
                <div className="col-sm-4">
                    <h1>Twitter</h1>
                    <TweetForm />
                    <TweetList />
                </div>
                <div className="col-sm-4"></div>
            </div>
        )
    }
});

ReactDOM.render(
    <TweetBox />,
    document.getElementById('content')
);