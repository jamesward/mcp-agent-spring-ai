# Sample: Spring AI with Bedrock and MCP

## Setup

1. Setup Bedrock in the AWS Console, [request access to Nova Pro](https://us-east-1.console.aws.amazon.com/bedrock/home?region=us-east-1#/modelaccess)
1. [Setup auth for local development](https://docs.aws.amazon.com/cli/v1/userguide/cli-chap-authentication.html)

## Running

Start the MCP Server:
```
./gradlew :server:bootRun
```

Start the MCP Client / Agent:
```
./gradlew :client:bootRun
```

Make a request to the server REST endpoint:

In IntelliJ, open the `client.http` file and run the request.

Or via `curl`:
```
curl -X POST --location "http://localhost:8080/inquire" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d 'question=Get employees that have skills related to Java, but not Java'
```
