import { SettingsTabType } from './tab';
import { CircleUserRound, Palette } from 'lucide-react';

interface SettingsSidebarTabData {
  icon: React.ReactNode;
  name: string;
}

export const settingsSidebarData: {
  [key in SettingsTabType]: SettingsSidebarTabData;
} = {
  [SettingsTabType.ACCOUNT]: {
    icon: <CircleUserRound size={18} />,
    name: 'Account',
  },
  [SettingsTabType.THEME]: {
    icon: <Palette size={18} />,
    name: 'Theme',
  },
};
