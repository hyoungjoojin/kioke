import type { Store } from '.';
import { useBoundStore } from '@/components/provider/StoreProvider';
import type { Dashboard } from '@/types/dashboard';
import { produce } from 'immer';
import type { StateCreator } from 'zustand';
import { useShallow } from 'zustand/react/shallow';

interface DashboardState {
  isEditing: boolean;
  draft: Dashboard | undefined;
}

interface DashboardActions {
  setIsEditingDashboard: (isEditing: boolean) => void;
  setDashboardDraft: (dashboard: Dashboard) => void;
  updateDashboardDraft: (callback: (current: Dashboard) => Dashboard) => void;
}

export type DashboardSlice = DashboardState & DashboardActions;

const initial: DashboardState = {
  isEditing: false,
  draft: undefined,
};

export const createDashboardSlice: StateCreator<
  Store,
  [],
  [],
  DashboardSlice
> = (set) => ({
  ...initial,
  setIsEditingDashboard(isEditing) {
    set(
      produce((state: DashboardSlice) => {
        state.isEditing = isEditing;
      }),
    );
  },
  setDashboardDraft(dashboard) {
    set(
      produce((state: DashboardSlice) => {
        state.draft = dashboard;
      }),
    );
  },
  updateDashboardDraft(callback) {
    set(
      produce((state: DashboardSlice) => {
        if (state.draft) {
          state.draft = callback(state.draft);
        }
      }),
    );
  },
});

export const useDashboard = () => {
  return useBoundStore(
    useShallow((store) => ({
      isEditing: store.isEditing,
      draft: store.draft,
    })),
  );
};

export const useDashboardActions = () => {
  return useBoundStore(
    useShallow((store) => ({
      setDraft: store.setDashboardDraft,
      updateDraft: store.updateDashboardDraft,
      setIsEditingDraft: store.setIsEditingDashboard,
    })),
  );
};
