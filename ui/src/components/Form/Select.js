import { FormControl, FormHelperText, InputLabel, MenuItem, Select as MuiSelect } from '@material-ui/core'

const FormikSelect = ({ field, form, label, options, ...props }) => {
  const error = form.touched[field.name] && Boolean(form.errors[field.name])
  return (
    <FormControl variant="outlined" fullWidth error={error} {...field} {...props}>
      <InputLabel>{label}</InputLabel>
      <MuiSelect label={label} {...field} {...props}>
        {options.map(option =>
          <MenuItem key={option.value} value={option.value}>
            {option.label}
          </MenuItem>
        )}
      </MuiSelect>
      {error && <FormHelperText>{form.errors[field.name]}</FormHelperText>}
    </FormControl>
  )
}

export default FormikSelect

// import React from 'react'
// import MuiSelect from '@material-ui/core/Select'
// import {getIn} from "formik";
// import {FormControl, FormHelperText, InputLabel, MenuItem} from "@material-ui/core";
//
// const fieldToSelect = ({
//    backgroundColor,
//    custom,
//    disabled,
//    field: { onBlur: fieldOnBlur, ...field },
//    form: { errors, isSubmitting, touched },
//    helperText,
//    onBlur,
//    variant,
//    warning,
//    ...props
//   }) => {
//   const dirty = getIn(touched, field.name)
//   const fieldError = getIn(errors, field.name)
//   const showError = dirty && !!fieldError
//   return {
//     variant: variant,
//     error: showError,
//     helperText: showError ? fieldError : warning ?? helperText,
//     disabled: disabled ?? isSubmitting,
//     onBlur: (event) => onBlur ?? fieldOnBlur(event ?? field.name),
//     ...custom,
//     ...field,
//     ...props,
//   }
// }
//
// export const Select = ({ field, form, label, options, ...props }) => {
//   const selectProps = fieldToSelect({ field, form, ...props });
//   return (
//     <FormControl variant="outlined" fullWidth error={selectProps.error}>
//       <InputLabel>{label}</InputLabel>
//       <MuiSelect {...selectProps}>
//         {options.map((option) => (
//           <MenuItem key={option.value} value={option.value}>
//             {option.label}
//           </MenuItem>
//         ))}
//       </MuiSelect>
//       {selectProps.error && <FormHelperText>{selectProps.helperText}</FormHelperText>}
//     </FormControl>
//   )
// }
//
// export default Select
//
// Select.displayName = 'FormikSelectField'
// Select.tabIndex = 0


