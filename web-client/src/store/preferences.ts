import Theme from '@/constants/theme';
import { produce } from 'immer';
import { StateCreator } from 'zustand';

interface KiokePreferences {
  theme: Theme;
}

interface PreferencesState {
  current: KiokePreferences;
  snapshot: KiokePreferences | null;
}

interface PreferencesActions {
  commit: () => void;
  rollback: () => void;
  setTheme: (theme: Theme) => void;
}

export type PreferencesSlice = PreferencesState & {
  actions: PreferencesActions;
};

const initialState: PreferencesState = {
  current: {
    theme: Theme.SYSTEM,
  },
  snapshot: null,
};

export const createPreferencesSlice: StateCreator<
  PreferencesSlice,
  [],
  [],
  PreferencesSlice
> = (set, _) => ({
  ...initialState,
  actions: {
    commit: () => {
      set(
        produce((slice: PreferencesSlice) => {
          if (slice.snapshot !== null) {
            slice.snapshot = null;
          }
        }),
      );
    },
    rollback: () => {
      set(
        produce((slice: PreferencesSlice) => {
          if (slice.snapshot !== null) {
            slice.current = slice.snapshot;
            slice.snapshot = null;
          }
        }),
      );
    },
    setTheme: (theme: Theme) => {
      set(
        produce((slice: PreferencesSlice) => {
          if (slice.snapshot === null) {
            slice.snapshot = slice.current;
          }

          slice.current.theme = theme;
        }),
      );
    },
  },
});
