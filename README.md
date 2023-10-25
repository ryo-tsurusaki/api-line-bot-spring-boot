# line-bot-api
LINE DevelopersのWebhookに設定し、トーク内容をOpenAiを介して返却するAPI。

以下は、LINE Developersのチャンネル設定・OpenAIの利用登録・AWS App Runnerにデプロイし、Lineメッセージを送ったレスポンスサンプル。

<img width="370" alt="image" src="https://github.com/ryo-tsurusaki/api-line-bot-spring-boot/assets/136481507/48880f02-9dde-4c8f-be44-4b00978ab1e3">


### package構成

| package        | ディレクトリ      | 説明                                     |
|----------------|-------------|----------------------------------------|
| application    | controller  | Controller                             |
|                | interceptor | コントローラー共通処理                            |
|                | resource    | パラメータなど                                |
|                | security    | 認証関連やCSRFトークンなどの処理                     |
| domain         | model       | 値オブジェクトなどを格納したオブジェクト                   |
|                | repository  | インターフェース                               |
|                | service     | DomainService                          |
| infrastructure | composite   | 複数のdatasourceやexternalの結果を結合したりする場合に使用 |
|                | config      | 設定ファイル                                 |
|                | datasource  | DBなど                                   |
|                | external    | 外部APIなど                                |
|                | transfer    | 外部ストレージなど                              |

### 起動時に必要な環境変数
| name                    | value                      |
|-------------------------|----------------------------|
| OPEN_AI_KEY             | OpenAIのAPIKey              |
| LINE_BOT_CHANNEL_TOKEN  | LineDevelopersのチャンネルトークン   |
| LINE_BOT_CHANNEL_SECRET | LineDevelopersのチャンネルシークレット |

### 参考にさせていただいたサイト

- line-bot-sdk-java<br>
  https://github.com/line/line-bot-sdk-java

- OpenAI接続<br>
  https://qiita.com/sasakitomopi/items/38c01447715bbb409c66

- AppRunnerデプロイ<br>
  https://aws.amazon.com/jp/builders-flash/202303/spring-boot-app-with-apprunner-cdk/?awsf.filter-name=*all
