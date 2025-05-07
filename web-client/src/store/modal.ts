import { ModalType } from '@/constants/modal';
import { produce } from 'immer';
import { StateCreator } from 'zustand';

interface ModalState {
  open: boolean;
  type: ModalType;
}

interface ModalActions {
  openModal: (type: ModalType) => void;
  closeModal: () => void;
  toggleModal: () => void;
}

export type ModalSlice = ModalState & {
  actions: ModalActions;
};

const initialState: ModalState = {
  open: false,
  type: ModalType.NULL,
};

export const createModalSlice: StateCreator<ModalSlice, [], [], ModalSlice> = (
  set,
  _,
) => ({
  ...initialState,
  actions: {
    openModal: (type: ModalType) => {
      set(
        produce((state: ModalSlice) => {
          state.open = true;
          state.type = type;
        }),
      );
    },
    closeModal: () => {
      set(
        produce((state: ModalSlice) => {
          state.open = false;
        }),
      );
    },
    toggleModal: () => {
      set(
        produce((state: ModalSlice) => {
          state.open = !state.open;
        }),
      );
    },
  },
});
