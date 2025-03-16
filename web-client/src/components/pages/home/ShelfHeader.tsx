"use client";

import EditableTitle from "@/components/features/editor/EditableTitle";
import { Skeleton } from "@/components/ui/skeleton";
import { useShelvesQuery, useUpdateShelfMutation } from "@/hooks/query/shelf";
import { useSelectedShelf } from "@/hooks/store";

const ShelfHeaderSkeleton = () => {
  return <Skeleton className="h-12 w-64 my-10 rounded-lg" />;
};

export default function ShelfHeader() {
  const { data: shelves, isLoading, isError } = useShelvesQuery();
  const selectedShelf = useSelectedShelf(shelves);

  if (isLoading || isError || !selectedShelf) {
    return <ShelfHeaderSkeleton />;
  }

  const { mutate: updateShelf } = useUpdateShelfMutation(selectedShelf);

  return selectedShelf ? (
    <div className="h-16 my-10 flex justify-center items-center">
      <EditableTitle
        content={selectedShelf.name}
        onUpdate={(shelfName) => {
          if (shelfName !== selectedShelf.name) {
            updateShelf({ name: shelfName });
          }
        }}
      />
    </div>
  ) : (
    <ShelfHeaderSkeleton />
  );
}
