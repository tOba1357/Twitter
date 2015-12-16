var TweetNode = React.createClass({
    render: function () {
        return (
            <div className="tweet">
                <dl>
                    <dt>{this.props.author}</dt>
                    <dd>
                        {this.props.children}
                        <p className="text-right">{this.props.date}</p>
                    </dd>
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
    componentDidMount: function () {
        this.loadTweets();
        setInterval(this.loadTweets, 2000);
    },
    loadTweets: function() {
        self = this;
        $.ajax({
            type: "GET",
            url: self.props.getUrl,
            dataType: "json",
            success: function (result) {
                self.setState({tweets: result});
            },
            error: function (xhr, status, err) {
                console.error(xhr);
            }
        });
    },
    render: function () {
        var tweetNodes = this.state.tweets.map(function (tweet, key) {
            return (
                <TweetNode author={tweet.author} date={tweet.createDate} key={key}>
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
    formId: "tweet",
    saveTweet: function (e) {
        e.preventDefault();
        self = this;
        $.ajax({
            type: "POST",
            url: self.props.postUrl,
            data: $("#" + this.formId).serialize(),
            dataType: "json",
            success: function (result) {
                document.getElementById("tweet-content").value = "";
            },
            error: function (xhr, status, err) {
                console.error(xhr);
            }
        });
    },
    render: function () {
        return (
            <form id={this.formId}>
                <input type="hidden" name="userId" value="1"/>
                <p><textarea className="form-control" rows="4" placeholder="ツイート内容" name="content" id="tweet-content"></textarea></p>
                <a className="btn btn-primary pull-right" onClick={this.saveTweet}>送信</a>
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
                    <TweetForm postUrl={this.props.postUrl}/>
                    <TweetList getUrl={this.props.getUrl}/>
                </div>
                <div className="col-sm-4"></div>
            </div>
        )
    }
});

var indexContainer = document.getElementById("content");
var getUrl = indexContainer.getAttribute("getUrl");
var postUrl = indexContainer.getAttribute("postUrl");

ReactDOM.render(
    <TweetBox getUrl={getUrl}
              postUrl={postUrl} />,
    document.getElementById('content')
);