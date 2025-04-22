import { produce } from "immer";
import View from "@/constants/view";
import { StateCreator } from "zustand";

interface ViewState {
  currentView: View;
}

interface ViewActions {
  getCurrentView: () => View;
  setCurrentView: (view: View) => void;
}

export type ViewSlice = ViewState & {
  actions: ViewActions;
};

const initialState: ViewState = {
  currentView: View.HOME,
};

export const createViewSlice: StateCreator<ViewSlice, [], [], ViewSlice> = (
  set,
  get,
) => ({
  ...initialState,
  actions: {
    getCurrentView: () => {
      return get().currentView;
    },
    setCurrentView: (view: View) => {
      set(
        produce((state) => {
          state.currentView = view;
        }),
      );
    },
  },
});
