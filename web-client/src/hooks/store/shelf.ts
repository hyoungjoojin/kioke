import { useBoundStore } from '@/components/providers/StoreProvider';

export const useSelectedShelfId = () => {
  const selectedShelfId = useBoundStore((state) => state.selectedShelfId);
  const setSelectedShelfId = useBoundStore(
    (state) => state.actions.setSelectedShelfId,
  );

  return {
    selectedShelfId,
    setSelectedShelfId,
  };
};
