import type { Store } from '.';
import type { ModalType } from '@/components/modal/Modal';
import { useBoundStore } from '@/components/provider/StoreProvider';
import { produce } from 'immer';
import type { StateCreator } from 'zustand';
import { useShallow } from 'zustand/react/shallow';

interface ModalState {
  isModalOpen: boolean;
  modalType: ModalType | null;
}

interface ModalActions {
  openModal: (type: ModalType) => void;
  closeModal: () => void;
}

export type ModalSlice = ModalState & ModalActions;

const initial: ModalState = {
  isModalOpen: false,
  modalType: null,
};

export const createModalSlice: StateCreator<Store, [], [], ModalSlice> = (
  set,
) => ({
  ...initial,
  openModal: (type: ModalType) => {
    set(
      produce((state: ModalSlice) => {
        state.modalType = type;
        state.isModalOpen = true;
      }),
    );
  },
  closeModal: () => {
    set(
      produce((state: ModalSlice) => {
        state.isModalOpen = false;
      }),
    );
  },
});

export const useModal = () => {
  return useBoundStore(
    useShallow((store) => ({
      open: store.isModalOpen,
      type: store.modalType,
    })),
  );
};

export const useModalActions = () => {
  return useBoundStore(
    useShallow((store) => ({
      openModal: store.openModal,
      closeModal: store.closeModal,
    })),
  );
};
