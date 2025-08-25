import { cn } from '@/lib/utils';

interface SpinnerProps {
  className?: string;
}
function Spinner({ className }: SpinnerProps) {
  return (
    <div
      className={cn(
        'w-3 h-3 border-[2px] border-black border-b-transparent rounded-full animate-spin',
        className,
      )}
    />
  );
}

export { Spinner };
