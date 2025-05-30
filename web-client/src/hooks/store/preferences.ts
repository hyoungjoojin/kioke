import { useBoundStore } from '@/components/providers/StoreProvider';
import { useShallow } from 'zustand/react/shallow';

function usePreferencesStore() {
  return useBoundStore(
    useShallow((slice) => {
      return {
        preferences: slice.current,
        setTheme: slice.actions.setTheme,
        commit: slice.actions.commit,
        rollback: slice.actions.rollback,
      };
    }),
  );
}

export default usePreferencesStore;
