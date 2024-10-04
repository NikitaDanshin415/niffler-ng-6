package guru.qa.niffler.config;

import org.apache.kafka.common.protocol.types.Field;

public interface Config {

  static Config getInstance() {
    return LocalConfig.INSTANCE;
  }

  String frontUrl();
  String authUrl();
  String gatewayUrl();
  String userdataUrl();
  String spendUrl();
  String githubUrl();
}
