import { useBoundStore } from "@/components/providers/StoreProvider";
import { Shelf } from "@/types/primitives/shelf";

export const useSelectedShelf = (shelves: Shelf[] | undefined) => {
  const selectedShelf = useBoundStore((state) =>
    state.actions.getSelectedShelf(shelves),
  );

  return selectedShelf;
};

export const useSelectedShelfIndex = () => {
  const selectedShelfIndex = useBoundStore((state) => state.selectedShelfIndex);

  const setSelectedShelfIndex = useBoundStore(
    (state) => state.actions.setSelectedShelfIndex,
  );

  return { selectedShelfIndex, setSelectedShelfIndex };
};

export const useTransactionStatus = () => {
  const { status, actions } = useBoundStore((state) => state);

  return {
    status,
    setStatus: actions.setStatus,
  };
};
