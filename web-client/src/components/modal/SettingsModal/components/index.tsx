import { SettingsSidebarItem } from './sidebar';
import { SettingsTab } from './tab';

export { SettingsTab } from './tab';

export const settingsSidebarItems: {
  [key in SettingsTab]: SettingsSidebarItem;
} = {
  [SettingsTab.ACCOUNT]: {
    icon: null,
    name: 'Account',
  },
  [SettingsTab.THEME]: {
    icon: null,
    name: 'Theme',
  },
};

export const settingsTabs: {
  [key in SettingsTab]: React.ReactNode;
} = {
  [SettingsTab.ACCOUNT]: <>Account</>,
  [SettingsTab.THEME]: <>Theme</>,
};
