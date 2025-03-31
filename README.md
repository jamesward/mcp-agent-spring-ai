# Sample: Spring AI with Bedrock and MCP

## Setup

1. Setup Bedrock in the AWS Console, [request access to Nova Pro](https://us-east-1.console.aws.amazon.com/bedrock/home?region=us-east-1#/modelaccess)
1. [Setup auth for local development](https://docs.aws.amazon.com/cli/v1/userguide/cli-chap-authentication.html)

## Run Locally

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
    -H "Content-Type: application/json" \
    -d '{"question": "Get employees that have skills related to Java, but not Java"}'
```

## Run on AWS

Prereqs:
- [Create an ECR Repo named `mcp-agent-spring-ai-server`](https://us-east-1.console.aws.amazon.com/ecr/private-registry/repositories/create?region=us-east-1)
- [Auth `docker` to ECR](https://docs.aws.amazon.com/AmazonECR/latest/userguide/registry_auth.html)

Build and push the MCP Server to ECR:
```
export ECR_REPO=<your account id>.dkr.ecr.us-east-1.amazonaws.com/mcp-agent-spring-ai-server

./gradlew :server:bootBuildImage --imageName=$ECR_REPO

docker push $ECR_REPO:latest
```

Deploy the Agent:
```
sam deploy template.yaml --stack-name mcp-agent-spring-ai --region=us-east-1 --capabilities CAPABILITY_IAM --resolve-s3
```

End-to-end Test with `curl`:
```
curl -X POST --location "https://YOUR_API_GATEWAY/Prod/inquire" \
-H "Content-Type: application/json" \
-d '{"question": "Get employees that have skills related to Java, but not Java"}'
```
