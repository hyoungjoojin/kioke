export enum SettingsTabType {
  ACCOUNT = 'account',
  THEME = 'theme',
}

export const settingsTabData: {
  [key in SettingsTabType]: React.ReactNode;
} = {
  [SettingsTabType.ACCOUNT]: <>Account</>,
  [SettingsTabType.THEME]: <>Theme</>,
};
