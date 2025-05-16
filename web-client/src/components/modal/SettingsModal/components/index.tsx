import { SettingsSidebarItem } from './sidebar';
import { SettingsTab } from './tab';
import { CircleUserRound, Palette } from 'lucide-react';

export { SettingsTab } from './tab';

export const settingsSidebarItems: {
  [key in SettingsTab]: SettingsSidebarItem;
} = {
  [SettingsTab.ACCOUNT]: {
    icon: <CircleUserRound size={18} />,
    name: 'Account',
  },
  [SettingsTab.THEME]: {
    icon: <Palette size={18} />,
    name: 'Theme',
  },
};

export const settingsTabs: {
  [key in SettingsTab]: React.ReactNode;
} = {
  [SettingsTab.ACCOUNT]: <>Account</>,
  [SettingsTab.THEME]: <>Theme</>,
};
