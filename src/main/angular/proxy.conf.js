const PROXY_CONFIG = [
    {
        context: [
            "/login",
            "/user",
            "/idea",
            "/comment"
        ],
        target: "http://localhost:8080",
        secure: false
    }
]

module.exports = PROXY_CONFIG;