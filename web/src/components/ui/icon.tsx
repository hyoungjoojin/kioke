import {
  ChevronDown,
  ChevronLeft,
  ChevronRight,
  ChevronUp,
  ListFilter,
  type LucideProps,
  Plus,
} from 'lucide-react';
import type { ForwardRefExoticComponent, RefAttributes } from 'react';

type IconName =
  | 'plus'
  | 'filter'
  | 'chevron-left'
  | 'chevron-right'
  | 'chevron-up'
  | 'chevron-down';

type IconProps = LucideProps & {
  name: IconName;
};

function Icon({ name, ...props }: IconProps) {
  const Component = map[name];
  return <Component {...props} />;
}

const map: Record<
  IconName,
  ForwardRefExoticComponent<
    Omit<LucideProps, 'ref'> & RefAttributes<SVGSVGElement>
  >
> = {
  plus: Plus,
  filter: ListFilter,
  'chevron-left': ChevronLeft,
  'chevron-right': ChevronRight,
  'chevron-up': ChevronUp,
  'chevron-down': ChevronDown,
};

export default Icon;
export type { IconName };
