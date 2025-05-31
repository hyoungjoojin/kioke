import View from '@/constants/view';
import { produce } from 'immer';
import { createStore } from 'zustand';

type ViewState = {
  currentView: View;
};

type ViewActions = {
  setCurrentView: (view: View) => void;
};

type ViewStore = ViewState & {
  actions: ViewActions;
};

const initialState: ViewState = {
  currentView: View.HOME,
};

const createViewStore = () => {
  return createStore<ViewStore>((set) => ({
    ...initialState,
    actions: {
      setCurrentView: (view: View) => {
        set(
          produce((store: ViewStore) => {
            store.currentView = view;
          }),
        );
      },
    },
  }));
};

export { type ViewStore, createViewStore };
