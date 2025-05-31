import Theme from '@/constants/theme';
import { produce } from 'immer';
import { createStore } from 'zustand';

type PreferencesState = {
  theme: Theme;
};

type PreferencesActions = {
  setTheme: (theme: Theme) => void;
};

type PreferencesStore = PreferencesState & {
  actions: PreferencesActions;
};

const initialState: PreferencesState = {
  theme: Theme.SYSTEM,
};

const createPreferencesStore = () => {
  return createStore<PreferencesStore>((set) => ({
    ...initialState,
    actions: {
      setTheme: (theme: Theme) => {
        set(
          produce((store: PreferencesStore) => {
            store.theme = theme;
          }),
        );
      },
    },
  }));
};

export { type PreferencesStore, createPreferencesStore };
