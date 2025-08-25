import type { Store } from '.';
import { useBoundStore } from '@/components/provider/StoreProvider';
import type { Dashboard } from '@/types/dashboard';
import { produce } from 'immer';
import type { StateCreator } from 'zustand';
import { useShallow } from 'zustand/react/shallow';

interface DashboardState {
  isEditingDashboard: boolean;
  currentDashboardState: Dashboard | null;
}

interface DashboardActions {
  setIsEditingDashboard: (isEditing: boolean) => void;
  initDashboardDraft: (dashboard: Dashboard) => void;
  updateDashboardDraft: (callback: (current: Dashboard) => Dashboard) => void;
}

export type DashboardSlice = DashboardState & DashboardActions;

const initial: DashboardState = {
  isEditingDashboard: false,
  currentDashboardState: null,
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
        state.isEditingDashboard = isEditing;
      }),
    );
  },
  initDashboardDraft(dashboard) {
    set(
      produce((state: DashboardSlice) => {
        state.currentDashboardState = dashboard;
      }),
    );
  },
  updateDashboardDraft(callback) {
    set(
      produce((state: DashboardSlice) => {
        if (state.currentDashboardState) {
          state.currentDashboardState = callback(state.currentDashboardState);
        }
      }),
    );
  },
});

export const useDashboard = () => {
  return useBoundStore(
    useShallow((store) => ({
      isEditing: store.isEditingDashboard,
      dashboardDraft: store.currentDashboardState,
    })),
  );
};

export const useDashboardActions = () => {
  return useBoundStore(
    useShallow((store) => ({
      initDashboard: store.initDashboardDraft,
      updateDashboardDraft: store.updateDashboardDraft,
      setIsEditingDraft: store.setIsEditingDashboard,
    })),
  );
};
